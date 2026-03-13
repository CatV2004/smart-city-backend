-- ===============================
-- Seed default categories
-- ===============================

INSERT INTO categories (name, slug, description, icon, color, ai_class)
VALUES
('Ổ gà', 'pothole', 'Ổ gà trên mặt đường', 'alert-triangle', '#ef4444', 'pothole'),

('Nứt mặt đường', 'road-crack', 'Mặt đường bị nứt hoặc hư hỏng', 'construction', '#f97316', 'road_crack'),

('Rác thải', 'garbage', 'Rác thải hoặc đổ rác trái phép', 'trash-2', '#22c55e', 'garbage'),

('Vẽ bậy', 'graffiti', 'Vẽ bậy lên tường hoặc công trình', 'spray-can', '#a855f7', 'graffiti'),

('Cây đổ', 'fallen-tree', 'Cây đổ chắn đường hoặc vỉa hè', 'tree-pine', '#16a34a', 'fallen_tree'),

('Biển báo hư hỏng', 'damaged-sign', 'Biển báo giao thông bị hỏng', 'signpost', '#eab308', 'damaged_sign'),

('Cột điện hư hỏng', 'damaged-electric-pole', 'Cột điện bị nghiêng hoặc hỏng', 'zap', '#facc15', 'damaged_electric_pole');