package com.reda.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CustomerRowMapperTest{

    @Test
    void mapRow() throws Exception {
        //GIVEN
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("reda");
        when(resultSet.getInt("age")).thenReturn(22);
        when(resultSet.getString("email")).thenReturn("reda@test");
        when(resultSet.getString("gender")).thenReturn("male");

        //WHEN
        Customer actual = customerRowMapper.mapRow(resultSet,1);
        Customer expected = new Customer(1,
                "reda",
                22,
                "reda@test",
                "password",
                "male");
        //THEN
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getAge()).isEqualTo(expected.getAge());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getGender()).isEqualTo(expected.getGender());
    }
}