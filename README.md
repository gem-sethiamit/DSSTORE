# DSSTORE

![image](https://user-images.githubusercontent.com/126468223/225543043-1f28f63f-7f76-4d9e-b3a2-71cdef4c9c1b.png)

DEPARTMENTAL STORE

Here we build CRUD operations for Customers, Products, Orders, and backorders for our DSSTORE Client.

Here a customer can book multiple orders for different products but one product at a time and one product can also have multiple orders from different customers. 
If we don't have sufficient product count to complete the order then we add our customer's order to backOrders and give prioritized delivery when products are added. 
We also give Discount of 100rs if your order amount is greater then 999 rs.

In Crud operations we made:
CUSTOMER :
1 - createCustomer.
2 - updateCustomer.
3 - deleteCustomer.
4 - GetCustomer by Id.
5 - GetAllCustomers.
PRODUCT :
1 - createProduct.
2 - updateProduct.
3 - deleteProduct.
4 - GetProduct by Id.
5 - GetAllProducts.
6 - AddProductCount.
ORDERS :
1 - createOrder.
2 - updateOrder.
3 - deleteOrder.
4 - GetOrder by Id.
5 - GetAllOrders.
BACKORDER :
1 - GetOrder by Id.
2 - GetAllOrders.

And more features are added according to the need please can check our documentation here : http://localhost:9090/swagger-ui/index.html#/


 

