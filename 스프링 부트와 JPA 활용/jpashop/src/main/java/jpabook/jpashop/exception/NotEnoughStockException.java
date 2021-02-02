package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String need_more_stock) {
        super(need_more_stock);
    }
}
