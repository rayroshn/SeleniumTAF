package com.test.automation.data;

import com.github.javafaker.Faker;
import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDataBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String company;

    private static final Faker faker = new Faker();

    public static TestDataBuilder generateRandomData() {
        return TestDataBuilder.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .address(faker.address().fullAddress())
                .company(faker.company().name())
                .build();
    }
}
