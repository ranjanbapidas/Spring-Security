package com.ranjan.testing.security.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ranjan.testing.security.dto.Product;
import com.ranjan.testing.security.entity.UserInfo;
import com.ranjan.testing.security.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {
	
	List<Product> productList = null;
	
	@Autowired
	private UserInfoRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostConstruct
	public void loadProductsFromDB() {
		productList = IntStream.rangeClosed(1, 100)
				.mapToObj(i-> Product.builder()
						.productId(i)
						.name("Product"+i)
						.qty(new Random().nextInt(10))
						.price(new Random().nextInt(5000))
						.build()).collect(Collectors.toList());
	}

	public List<Product> getproducts() {
		
		return productList;
	}

	public Product getProduct(int id) {
		// TODO Auto-generated method stub
		return productList.stream()
				.filter(p->p.getProductId()==id)
				.findAny()
				.orElseThrow(()->new RuntimeException("Product "+id+" not found"));
	}
	
	
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		repository.save(userInfo);
		return "User Added to System";
		
	}

}
