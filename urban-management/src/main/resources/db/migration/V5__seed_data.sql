-- ===============================
-- Seed departments
-- ===============================
INSERT INTO departments (name, code, description, is_active)
VALUES
('Sở Giao thông Vận tải', 'TRANSPORT', 'Quản lý giao thông, đường xá, cầu cống', TRUE),
('Sở Tài nguyên và Môi trường', 'ENVIRONMENT', 'Quản lý môi trường, rác thải, vệ sinh', TRUE),
('Sở Văn hóa và Thể thao', 'CULTURE', 'Quản lý văn hóa, nghệ thuật, di tích', TRUE),
('Sở Công Thương', 'INDUSTRY_TRADE', 'Quản lý điện lực, công nghiệp, thương mại', TRUE),
('Sở Xây dựng', 'CONSTRUCTION', 'Quản lý xây dựng, công trình, hạ tầng', TRUE),
('Ban Quản lý công viên cây xanh', 'PARKS', 'Quản lý công viên, cây xanh đô thị', TRUE),
('Tổng đài khẩn cấp', 'EMERGENCY', 'Xử lý các vấn đề khẩn cấp, chưa rõ phân loại', TRUE)
ON CONFLICT (code) DO NOTHING;

-- ===============================
-- Seed categories
-- ===============================
DO $$
DECLARE
    transport_id UUID;
    environment_id UUID;
    culture_id UUID;
    industry_id UUID;
    construction_id UUID;
    parks_id UUID;
    emergency_id UUID;
BEGIN
    SELECT id INTO transport_id FROM departments WHERE code = 'TRANSPORT';
    SELECT id INTO environment_id FROM departments WHERE code = 'ENVIRONMENT';
    SELECT id INTO culture_id FROM departments WHERE code = 'CULTURE';
    SELECT id INTO industry_id FROM departments WHERE code = 'INDUSTRY_TRADE';
    SELECT id INTO construction_id FROM departments WHERE code = 'CONSTRUCTION';
    SELECT id INTO parks_id FROM departments WHERE code = 'PARKS';
    SELECT id INTO emergency_id FROM departments WHERE code = 'EMERGENCY';

    INSERT INTO categories (name, slug, description, icon, color, ai_class, department_id) VALUES

    -- TRANSPORT department
    ('Ổ gà', 'pothole', 'Ổ gà trên mặt đường', 'AlertTriangle', '#ef4444', 'pothole', transport_id),
    ('Nứt mặt đường', 'road-crack', 'Mặt đường bị nứt hoặc hư hỏng', 'Construction', '#f97316', 'road_crack', transport_id),
    ('Biển báo hư hỏng', 'damaged-sign', 'Biển báo giao thông bị hỏng', 'Dam', '#eab308', 'damaged_sign', transport_id),

    -- ENVIRONMENT department
    ('Rác thải', 'garbage', 'Rác thải hoặc đổ rác trái phép', 'Repeat', '#22c55e', 'garbage', environment_id),

    -- CULTURE department
    ('Vẽ bậy', 'graffiti', 'Vẽ bậy lên tường hoặc công trình', 'PenTool', '#a855f7', 'graffiti', culture_id),

    -- PARKS department
    ('Cây đổ', 'fallen-tree', 'Cây đổ chắn đường hoặc vỉa hè', 'TreePine', '#16a34a', 'fallen_tree', parks_id),

    -- INDUSTRY department
    ('Cột điện hư hỏng', 'damaged-electric-pole', 'Cột điện bị nghiêng hoặc hỏng', 'Rat', '#facc15', 'damaged_electric_pole', industry_id),

    -- EMERGENCY department
    ('Khác', 'other', 'Các vấn đề khác không thuộc danh mục trên', 'HelpCircle', '#94a3b8', 'other', emergency_id);

END $$;