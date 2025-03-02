package com.goktug.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.CurrencyRateResponse;
import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoCustomer;
import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoSaledCar;
import com.goktug.dto.DtoSaledCarIU;
import com.goktug.enums.CarStatus;
import com.goktug.enums.CurrencyType;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.models.Car;
import com.goktug.models.Customer;
import com.goktug.models.SaledCar;
import com.goktug.repositories.CarRepository;
import com.goktug.repositories.CustomerRepository;
import com.goktug.repositories.GalleristRepository;
import com.goktug.repositories.SaledCarRepository;
import com.goktug.services.ISaledCarService;

@Service
public class SaledCarService implements ISaledCarService {

    @Autowired
    private SaledCarRepository saledCarRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GalleristRepository gallerisRepository;

    @Autowired
    private CurrencyRateService currencyRateService;

    private String getCurrencyRateValue() {
        LocalDate currencyRateDate;
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();

        if (day == DayOfWeek.SUNDAY) {
            currencyRateDate = currentDate.minusDays(2);
        } else if (day == DayOfWeek.SATURDAY) {
            currencyRateDate = currentDate.minusDays(1);
        } else {
            currencyRateDate = currentDate;
        }

        String formattedDate = currencyRateDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();

        CurrencyRateResponse currencyRateResponse = currencyRateService.getCurrencyRate(formattedDate, formattedDate);

        return currencyRateResponse.getItems().get(0).getUsd();
    }

    public BigDecimal convertCustomerAmountToUSD(Customer customer) {

        BigDecimal usd = new BigDecimal(getCurrencyRateValue());

        BigDecimal customerUSDAmount = customer.getAccount().getAmount().divide(usd, 2, RoundingMode.HALF_UP);

        return customerUSDAmount;
    }

    public BigDecimal convertCarPriceToUSD(Car car) {

        BigDecimal usd = new BigDecimal(getCurrencyRateValue());

        BigDecimal carUSDPrice = car.getPrice().divide(usd, 2, RoundingMode.HALF_UP);

        return carUSDPrice;
    }

    public boolean checkAmount(DtoSaledCarIU saledCar) {
        Optional<Customer> optCustomer = customerRepository.findById(saledCar.getCustomerId());

        if (optCustomer.isEmpty()) {
            throw new BaseException(new ErrorMessage(saledCar.getCustomerId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        Optional<Car> optCar = carRepository.findById(saledCar.getCarId());

        if (optCar.isEmpty()) {
            throw new BaseException(new ErrorMessage(saledCar.getCarId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        BigDecimal customerUSDAmount = optCustomer.get().getAccount().getAmount();
        if (optCustomer.get().getAccount().getCurrencyType().equals(CurrencyType.TL)) {
            customerUSDAmount = convertCustomerAmountToUSD(optCustomer.get());

        }
        return customerUSDAmount.compareTo(optCar.get().getPrice()) >= 0 ? true : false;

    }

    private SaledCar createSaledCar(DtoSaledCarIU saledCar) {

        SaledCar newSaledCar = new SaledCar();

        newSaledCar.setCreatedAt(new Date());
        newSaledCar.setCustomer(customerRepository.findById(saledCar.getCustomerId()).orElse(null));
        newSaledCar.setGallerist(gallerisRepository.findById(saledCar.getGalleristId()).orElse(null));
        newSaledCar.setCar(carRepository.findById(saledCar.getCarId()).orElse(null));

        return newSaledCar;
    }

    public boolean checkCarStatus(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);

        if (car != null && car.getCarStatus().name().equals(CarStatus.SALED.name())) {
            return false;
        }
        return true;
    }

    public BigDecimal remainingCustomerAmount(Customer customer, Car car) {
        BigDecimal customerUSDAmount = customer.getAccount().getAmount();
        if (customer.getAccount().getCurrencyType().equals(CurrencyType.TL)) {
            customerUSDAmount = convertCustomerAmountToUSD(customer);
        }
        BigDecimal carUSDPrice = car.getPrice();
        if (car.getCurrencyType().equals(CurrencyType.TL)) {
            carUSDPrice = convertCarPriceToUSD(car);

        }
        BigDecimal remainingCustomerUSDAmount = customerUSDAmount.subtract(carUSDPrice);

        BigDecimal usd = new BigDecimal(getCurrencyRateValue());

        return customer.getAccount().getCurrencyType().equals(CurrencyType.TL)
                ? remainingCustomerUSDAmount.multiply(usd)
                : remainingCustomerUSDAmount;
    }

    @Override
    public DtoSaledCar buyCar(DtoSaledCarIU saledCar) {

        if (!checkCarStatus(saledCar.getCarId())) {
            throw new BaseException(
                    new ErrorMessage(saledCar.getCarId().toString(), MessageType.CAR_STATUS_IS_ALREADY_SALED),
                    400);
        }

        if (!checkAmount(saledCar)) {
            throw new BaseException(new ErrorMessage("", MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH),
                    400);
        }

        SaledCar dbSaledCar = saledCarRepository.save(createSaledCar(saledCar));

        Car car = dbSaledCar.getCar();
        car.setCarStatus(CarStatus.SALED);

        carRepository.save(car);

        Customer customer = dbSaledCar.getCustomer();

        customer.getAccount().setAmount(remainingCustomerAmount(customer, car));

        customerRepository.save(customer);

        return toDTO(dbSaledCar);
    }

    private DtoSaledCar toDTO(SaledCar saledCar) {
        DtoCustomer dtoCustomer = new DtoCustomer();
        DtoCar dtoCar = new DtoCar();
        DtoGallerist dtoGallerist = new DtoGallerist();
        DtoAddress dtoAddress = new DtoAddress();
        DtoAddress dtoCustomerAddress = new DtoAddress();
        DtoAccount dtoAccount = new DtoAccount();

        DtoSaledCar dtoSaledCar = new DtoSaledCar();

        BeanUtils.copyProperties(saledCar.getGallerist().getAddress(), dtoAddress);

        BeanUtils.copyProperties(saledCar.getGallerist(), dtoGallerist);

        dtoGallerist.setAddress(dtoAddress);

        BeanUtils.copyProperties(saledCar.getCar(), dtoCar);

        BeanUtils.copyProperties(saledCar.getCustomer().getAccount(), dtoAccount);
        dtoCustomer.setAccount(dtoAccount);
        BeanUtils.copyProperties(saledCar.getCustomer().getAddress(), dtoCustomerAddress);
        dtoCustomer.setAddress(dtoCustomerAddress);
        BeanUtils.copyProperties(saledCar.getCustomer(), dtoCustomer);

        dtoSaledCar.setCar(dtoCar);
        dtoSaledCar.setCustomer(dtoCustomer);
        dtoSaledCar.setGallerist(dtoGallerist);

        return dtoSaledCar;

    }
}
