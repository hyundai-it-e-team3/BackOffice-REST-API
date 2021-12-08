-- 상품 정보
CREATE OR REPLACE VIEW orderDetail_view AS
    SELECT order_id, 
                count(*)  TOTAL_PRODUCT,
                count ( CASE WHEN state = '1' THEN order_id END ) STATE1,
                count ( CASE WHEN state = '2' THEN order_id END ) STATE2,
                count ( CASE WHEN state = '3' THEN order_id END ) STATE3,
                count ( CASE WHEN state = '4' THEN order_id END ) STATE4,
                count ( CASE WHEN state = '5' THEN order_id END ) STATE5
    FROM order_detail
    GROUP BY order_id ;


CREATE OR REPLACE VIEW payment_view AS
    SELECT order_id, SUM(price) total_price, MAX(state) pay_type
    FROM payment
    GROUP BY order_id ;

CREATE OR REPLACE VIEW order_view AS
    SELECT order_id, order_date, member_id, state order_state
    FROM morder
    ORDER BY order_date DESC;
    
    
    
    
    
    
SELECT o.order_id, o.order_date, o.member_id, o.order_state, od.total_product, od.state1, od.state2, od.state3, od.state4, od.state5, p.total_price, p.pay_type
FROM order_view o, orderdetail_view od, payment_view p
WHERE o.order_id = od.order_id 
AND p.order_id = od.order_id
ORDER BY order_date DESC;