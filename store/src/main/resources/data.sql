INSERT INTO stores (id, name, address, city, state, zip_code, phone, email, is_active, last_sync_time, created_at, updated_at) VALUES
(1, 'Downtown Electronics', '123 Main Street', 'San Francisco', 'CA', '94105', '(415) 555-0001', 'downtown@electronics.com', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Mall Tech Center', '456 Shopping Mall Way', 'Los Angeles', 'CA', '90210', '(213) 555-0002', 'mall@electronics.com', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Tech Hub', '789 Innovation Drive', 'Seattle', 'WA', '98101', '(206) 555-0003', 'hub@electronics.com', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO products (id, name, sku, category, price, created_at, updated_at) VALUES
(1, 'Laptop Dell XPS 13', 'DELL-XPS-13', 'Electronics', 1299.99, NOW(), NOW()),
(2, 'iPhone 15 Pro', 'APPLE-IP15P', 'Electronics', 999.99, NOW(), NOW()),
(3, 'Nike Air Max 90', 'NIKE-AM90', 'Footwear', 119.99, NOW(), NOW()),
(4, 'Samsung 4K TV 55"', 'SAMSUNG-TV55', 'Electronics', 799.99, NOW(), NOW()),
(5, 'Adidas Ultraboost 22', 'ADIDAS-UB22', 'Footwear', 189.99, NOW(), NOW()),
(6, 'Sony WH-1000XM5 Headphones', 'SONY-WH1000XM5', 'Electronics', 399.99, NOW(), NOW()),
(7, 'Levi''s 501 Jeans', 'LEVIS-501', 'Clothing', 69.99, NOW(), NOW()),
(8, 'MacBook Pro 16"', 'APPLE-MBP16', 'Electronics', 2499.99, NOW(), NOW()),
(9, 'Canon EOS R5 Camera', 'CANON-EOSR5', 'Electronics', 3899.99, NOW(), NOW()),
(10, 'Patagonia Down Jacket', 'PATAGONIA-DJ', 'Clothing', 299.99, NOW(), NOW());

INSERT INTO store_inventory (id, product_id, store_id, total_stock, available_stock, reserved_stock, updated_at) VALUES
(1, 1, 1, 25, 25, 0, NOW()),
(2, 2, 1, 50, 50, 0, NOW()),
(3, 3, 3, 30, 30, 0, NOW()),
(4, 4, 1, 15, 15, 0, NOW()),
(5, 5, 1, 40, 40, 0, NOW()),
(6, 6, 1, 20, 20, 0, NOW()),
(7, 7, 2, 60, 60, 0, NOW()),
(8, 8, 1, 10, 10, 0, NOW()),
(9, 9, 1, 8, 8, 0, NOW()),
(10,10, 2, 35, 35, 0, NOW());
