package com.reda.customer;

import com.reda.exception.DuplicateResourceException;
import com.reda.exception.NotFoundException;
import com.reda.exception.RequestValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private DaoCustomerInt daoCustomerInt;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(daoCustomerInt);
    }


    @Test
    void getAllCustomers() {
        //WHEN
        underTest.getAllCustomers();

        //THEN
        verify(daoCustomerInt).selectAllCustomers();
    }

    @Test
    void getCustomersById() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                "com/reda/customer/testContConfig",
                22,
                "reda@test-Mockito",
                "male"
        );
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(customer));
        //WHEN
        Customer actual = underTest.getCustomersById(id);

        //THEN
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void whenGetCustomersByIdReturnEmptyOptional() {
        //GIVEN
        int id = 1;
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.empty());
        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getCustomersById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        //GIVEN
        String email = "reda@test.com";
        when(daoCustomerInt.existsCustomerWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "com/reda/customer/testContConfig",21,email,"female"
        );
        //WHEN
        //ajouter customer
        underTest.addCustomer(request);
        //THEN
        //Creer un argument de capture
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        //verifier si dans addCustomer DaoCustomerInt appelle la methode insertCustomer et
        // en meme temps capturer l'objet inserer
        verify(daoCustomerInt).insertCustomer(customerArgumentCaptor.capture());

        //Creer un objet Customer avec les donnees qui ont ete capturer
        Customer customerCaptured = customerArgumentCaptor.getValue();
        //Verifier si l'id et null parceque il est auto gen et assurer que les donnees inserer
        //sont les memes donnes Request
        assertThat (customerCaptured.getId()).isNull();
        assertThat (customerCaptured.getName()).isEqualTo(request.name());
        assertThat (customerCaptured.getEmail()).isEqualTo(request.email());
        assertThat(customerCaptured.getAge()).isEqualTo(request.age());
        assertThat(customerCaptured.getGender()).isEqualTo(request.gender());
    }

    @Test
    void addCustomerReturnEmailExists(){
        //GIVEN
        String email = "reda@mockito";
        when(daoCustomerInt.existsCustomerWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "com/reda/customer/testContConfig",22,email,"male"
        );
        //WHEN
        assertThatThrownBy(()-> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("email already used!");
        //THEN
        //verifier si la methode n'a pas execute
        verify(daoCustomerInt,never()).insertCustomer(any());
    }
    @Test
    void deleteCustomer() {
        //GIVEN
        int id = 1;
        when(daoCustomerInt.existsCustomerWithId(id)).thenReturn(true);
        //WHEN
        underTest.deleteCustomer(id);
        //THEN
        verify(daoCustomerInt).deleteCustomer(id);
    }
    @Test
    void deleteCustomerWhenIdReturnFalse(){
        int id =1;
        when(daoCustomerInt.existsCustomerWithId(id)).thenReturn(false);


        assertThatThrownBy(()-> underTest.deleteCustomer(id) ).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("ID [%s] not found !".formatted(id));

        verify(daoCustomerInt, never()).deleteCustomer(id);
    }
    @Test
    void updateByIdWhenNameNotEqualNullAndChanged() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(
                id, "com/reda/customer/testContConfig",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration(
                "abdo", 50, "test","male");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));


        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        underTest.updateById(id,update);
        //THEN
        verify(daoCustomerInt).updateCustomerWithId(argumentCaptor.capture());
        Customer customerCapt = argumentCaptor.getValue();
        assertThat(customerCapt.getName()).isEqualTo(update.name());
        assertThat(customerCapt.getAge()).isEqualTo(update.age());
        assertThat(customerCapt.getEmail()).isEqualTo(update.email());
        assertThat(customerCapt.getGender()).isEqualTo(update.gender());

    }

    @Test
    void updateByIdWhenAgeNotEqualNullAndChanged() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(id, "com/reda/customer/testContConfig",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration("com/reda/customer/testContConfig", 22, "test","male");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        underTest.updateById(id,update);
        //THEN
        verify(daoCustomerInt).updateCustomerWithId(argumentCaptor.capture());
        Customer customerCapt = argumentCaptor.getValue();
        assertThat(customerCapt.getName()).isEqualTo(update.name());
        assertThat(customerCapt.getAge()).isEqualTo(update.age());
        assertThat(customerCapt.getEmail()).isEqualTo(update.email());
        assertThat(customerCapt.getGender()).isEqualTo(update.gender());
    }
    @Test
    void updateByIdWhenGenderNotEqualNullAndChanged() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(
                id, "abdo",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration(
                "abdo", 50, "test","female");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));


        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        underTest.updateById(id,update);
        //THEN
        verify(daoCustomerInt).updateCustomerWithId(argumentCaptor.capture());
        Customer customerCapt = argumentCaptor.getValue();
        assertThat(customerCapt.getName()).isEqualTo(update.name());
        assertThat(customerCapt.getAge()).isEqualTo(update.age());
        assertThat(customerCapt.getEmail()).isEqualTo(update.email());
        assertThat(customerCapt.getGender()).isEqualTo(update.gender());

    }
    @Test
    void updateByIdWhenEmailNotEqualNullAndChangedAndEmailNotExist() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(id, "com/reda/customer/testContConfig",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration("com/reda/customer/testContConfig", 50, "test@test","male");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));
        when(daoCustomerInt.existsCustomerWithEmail("test@test")).thenReturn(false);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        underTest.updateById(id,update);
        //THEN
        verify(daoCustomerInt).updateCustomerWithId(argumentCaptor.capture());
        Customer customerCapt = argumentCaptor.getValue();
        assertThat(customerCapt.getName()).isEqualTo(update.name());
        assertThat(customerCapt.getAge()).isEqualTo(update.age());
        assertThat(customerCapt.getEmail()).isEqualTo(update.email());
        assertThat(customerCapt.getGender()).isEqualTo(update.gender());
    }
    @Test
    void updateByIdWhenEmailNotEqualNullAndChangedAndEmailExist() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(id, "com/reda/customer/testContConfig",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration("com/reda/customer/testContConfig", 50, "test@test","male");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));
        when(daoCustomerInt.existsCustomerWithEmail("test@test")).thenReturn(true);


        assertThatThrownBy(()-> underTest.updateById(id,update)).isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Email already taken !");
        //THEN
        verify(daoCustomerInt,never()).updateCustomerWithId(any());
    }
    @Test
    void updateByIdNothingChanged() {
        //GIVEN
        int id = 1;
        Customer existingCustomer = new Customer(id, "com/reda/customer/testContConfig",50,"test","male");
        CustomerUpdateRegistration update = new CustomerUpdateRegistration("com/reda/customer/testContConfig", 50, "test","male");
        //WHEN
        when(daoCustomerInt.selectById(id)).thenReturn(Optional.of(existingCustomer));

        assertThatThrownBy(()-> underTest.updateById(id,update)).isInstanceOf(RequestValidationException.class)
                .hasMessageContaining("no data source changes !");
        //THEN
        verify(daoCustomerInt,never()).updateCustomerWithId(any());
    }
}