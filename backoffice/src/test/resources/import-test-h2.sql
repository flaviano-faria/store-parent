INSERT INTO categories (id, name, description) VALUES
    (1, 'Electronics', 'Electronic devices and accessories'),
    (2, 'Clothing', 'Apparel and fashion'),
    (3, 'Home & Garden', 'Home improvement and garden supplies');

INSERT INTO products (id, name, description, price, picture, sku, category_id) VALUES
    (101, 'Wireless Mouse', 'Ergonomic wireless mouse', 29.99, 'mouse.png', 'ELEC-MOU-001', 1),
    (102, 'USB-C Hub', '7-in-1 USB-C hub', 49.99, 'hub.png', 'ELEC-HUB-002', 1),
    (201, 'Cotton T-Shirt', 'Organic cotton tee', 24.99, 'tshirt.png', 'CLTH-TSH-001', 2),
    (202, 'Denim Jeans', 'Classic fit jeans', 79.99, 'jeans.png', 'CLTH-JNS-002', 2),
    (301, 'LED Lamp', 'Adjustable desk lamp', 39.99, 'lamp.png', 'HOME-LMP-001', 3);

ALTER TABLE categories ALTER COLUMN id RESTART WITH 4;
ALTER TABLE products ALTER COLUMN id RESTART WITH 302;
