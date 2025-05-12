package com.grpc.repository;

import com.grpc.entity.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock,Long> {
    Flux<Stock> findByStockSymbol(String stockSymbol);
}
