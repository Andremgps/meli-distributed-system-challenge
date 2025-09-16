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

INSERT INTO inventory (id, product_id, store_id, total_stock, available_stock, reserved_stock, reorder_level, updated_at, version) VALUES
(1, 1, 1, 25, 25, 0, 5, NOW(), 0),
(2, 2, 1, 50, 50, 0, 10, NOW(), 0),
(3, 3, 3, 30, 30, 0, 8, NOW(), 0),
(4, 4, 1, 15, 15, 0, 3, NOW(), 0),
(5, 5, 1, 40, 40, 0, 10, NOW(), 0),
(6, 6, 1, 20, 20, 0, 5, NOW(), 0),
(7, 7, 2, 60, 60, 0, 15, NOW(), 0),
(8, 8, 1, 10, 10, 0, 2, NOW(), 0),
(9, 9, 1, 8, 8, 0, 2, NOW(), 0),
(10,10, 2, 35, 35, 0, 8, NOW(), 0);