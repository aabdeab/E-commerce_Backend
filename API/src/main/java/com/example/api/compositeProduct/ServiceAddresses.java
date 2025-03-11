package com.example.api.compositeProduct;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//J'ai imposé ici le constructeur sans arguments meme pour les attributs (finals)
@NoArgsConstructor(force=true)
@Builder
@AllArgsConstructor
public class ServiceAddresses {
    private final String compositeAddress;
    private final String productAddress;
    private final String recommendationAddress;
    private final String reviewAddress;
}


