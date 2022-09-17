package kvbdev.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String fullAddress;

    public Address(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
