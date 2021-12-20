-- 상품 정보
CREATE OR REPLACE VIEW orderDetail_view AS
    SELECT order_id, 
                count(*)  TOTAL_PRODUCT,
                count ( CASE WHEN state_code = 1 THEN order_id END ) STATE1,
                count ( CASE WHEN state_code = 2 THEN order_id END ) STATE2,
                count ( CASE WHEN state_code = 3 THEN order_id END ) STATE3,
                count ( CASE WHEN state_code = 4 THEN order_id END ) STATE4,
                count ( CASE WHEN state_code = 5 THEN order_id END ) STATE5,
	    count ( CASE WHEN state_code = 6 THEN order_id END ) STATE6
    FROM order_detail
    GROUP BY order_id ;


CREATE OR REPLACE VIEW payment_view AS
    SELECT p.order_id, p.total_price, pt.type pay_type
    FROM
        (
        SELECT order_id, SUM(price) total_price, MAX(type_code) maxval
        FROM payment
        GROUP BY order_id ) p, pay_type pt
    WHERE p.maxval = pt.type_code;



CREATE OR REPLACE VIEW order_view AS
    SELECT o.order_id, o.order_date, o.member_id, os.state
    FROM morder o, order_state os
    WHERE o.state_code = os.state_code
    ORDER BY order_date DESC;
    
    
    
    
    
    
SELECT o.order_id, o.order_date, o.member_id, o.state, od.total_product, od.state1, od.state2, od.state3, od.state4, od.state5, p.total_price, p.pay_type
FROM order_view o, orderdetail_view od, payment_view p
WHERE o.order_id = od.order_id 
AND p.order_id = od.order_id
ORDER BY order_date DESC;







       SELECT o.rnum, o.order_id, o.order_date, o.member_id, o.order_state, od.total_product, od.state1, od.state2, od.state3, od.state4, od.state5, od.state6, p.total_price, p.pay_type
        FROM (
                SELECT ROWNUM rnum, order_id, order_date, member_id, order_state
                FROM (
                    SELECT order_id, order_date, member_id, order_state
                    FROM order_view
                    WHERE 
                        --order_id = 103
                        --order_date BETWEEN TO_DATE('21211207') AND TO_DATE('21211208')
                        --member_id = 'yena'
                        --order_state = '주문처리중'
                    ORDER BY order_date DESC
                    
                ) WHERE ROWNUM < 6) o, 
                orderdetail_view od, payment_view p
        WHERE o.order_id = od.order_id AND o.order_id = p.order_id 
        AND rnum >0
        ORDER BY rnum;










	<select id="selectBySearch" parameterType="orderSearch"  resultType="orderInfo">
		SELECT o.rnum, o.order_id, o.order_date, o.member_id, o.order_state, od.total_product, od.state1, od.state2, od.state3, od.state4, od.state5, od.state6, p.total_price, p.pay_type
       	FROM (
            SELECT ROWNUM rnum, order_id, order_date, member_id, order_state
            FROM (
                  SELECT order_id, order_date, member_id, order_state
                  FROM order_view
                  
                  <where>
					<if test="oStateList != null">
						AND order_state IN 
						<foreach item="oState" collection="oStateList" open="(" close=")" separator="," >
							#{oState}
						</foreach>
					</if>
					<if test="regStart != '' and regEnd != ''" >
			  			AND 
			  			<![CDATA[ 
			  			(order_date >= #{regStart} AND order_date <= #{regEnd})
			  			]]> 
			  		</if>
						
					<choose>
						<when test="searchType == 'order_id'">
							AND order_id like '%' || #{searchWord} || '%'
						</when>
						<when test="searchType == 'member_id'">
							AND member_id like '%' || #{searchWord} || '%'
						</when>
						<when test="searchType == 'None' ">
				   			AND (order_id like '%' || #{searchWord} || '%'
				   			OR member_id like '%' || #{searchWord} || '%')
			  			</when>
					</choose>
				  </where>
                    ) AND ROWNUM &lt;= #{pager.endRowNo}) o, 
                    orderdetail_view od, payment_view p
           			WHERE o.order_id = od.order_id AND o.order_id = p.order_id 
           			ORDER BY order_date DESC
            <if test="odStateList != null">
				<foreach item="odState" collection="odStateList">
					<choose>
						<when test="odState == '1'">
							AND od.state1 > 0
						</when>
						<when test="odState == '2'">
							AND od.state2 > 0
						</when>
						<when test="odState == '3'">
							AND od.state3 > 0
						</when>
						<when test="odState == '4'">
							AND od.state4 > 0
						</when>
						<when test="odState == '5'">
							AND od.state5 > 0
						</when>
						<when test="odState == '6'">
							AND od.state6 > 0
						</when>
					</choose>
				</foreach>
			</if>
            AND rnum &gt;= #{pager.startRowNo}
            ORDER BY rnum
	</select>




	<select id="countSearch" parameterType="orderSearch" resultType="int">
		SELECT count(*)
       	FROM order_view ov, orderdetail_view odv
       	WHERE ov.order_id = odv.order_id
      		<where>
				<if test="oStateList != null">
					 AND order_state IN 
					<foreach item="oState" collection="oStateList" open="(" close=")" separator="," >
						#{oState}
					</foreach>
				</if>
				<if test="regStart != '' and regEnd != ''" >
	    			AND 
	    			<![CDATA[ 
	    			(order_date >= #{regStart} AND order_date <= #{regEnd})
	    			]]> 
	    		</if>
				<choose>
					<when test="searchType == 'order_id'">
						AND order_id like '%' || #{searchWord} || '%'
					</when>
					<when test="searchType == 'member_id'">
						AND member_id like '%' || #{searchWord} || '%'
					</when>
					<when test="searchType == 'None' ">
		    			AND (order_id like '%' || #{searchWord} || '%'
		    			OR member_id like '%' || #{searchWord} || '%')
	    			</when>
				</choose>
			</where>
	</select>
            