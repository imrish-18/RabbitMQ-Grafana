package com.grpc.service;


import com.grpc.StockRequest;
import com.grpc.StockResponse;
import com.grpc.StockTradingServiceGrpc;
import com.grpc.entity.Stock;
import com.grpc.repository.StockRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {


    @Autowired
    private StockRepository stockRepository;


    @Override
    public void getStockPrice(StockRequest request,
                              StreamObserver<StockResponse> responseObserver) {

        //stockName -> DB -> map response -> return

        String stockSymbol = request.getStockSymbol();
        Flux<Stock> stockEntity = stockRepository.findByStockSymbol(stockSymbol);
        final StockResponse[] stockResponse = {null};
        stockEntity.subscribe(response->{
            stockResponse[0] = StockResponse.newBuilder()
                    .setStockSymbol(response.getStockSymbol())
                    .setPrice(response.getPrice())
                    .setTimestamp(response.getLastUpdated().toString())
                    .build();

        });
        responseObserver.onNext(stockResponse[0]);
        responseObserver.onCompleted();

    }
}
