# Quick Reference - NV Roles & PhieuNhap APIs

## 🚀 Quick Start

### Running the Application
```bash
mvn spring-boot:run
# or
mvn -DskipTests package
java -jar target/restaurant-0.0.1-SNAPSHOT.jar
```

### Base URL
```
http://localhost:8080/api
```

---

## 📌 API Endpoints Summary

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/nv-kho-bep` | POST | Create Kitchen Staff |
| `/nv-kho-bep` | GET | Get all Kitchen Staff |
| `/nv-kho-bep/{id}` | GET | Get Kitchen Staff by ID |
| `/nv-kho-bep/{id}` | PUT | Update Kitchen Staff |
| `/nv-kho-bep/{id}` | DELETE | Delete Kitchen Staff |
| `/nv-kho-bep/search` | POST | Search Kitchen Staff with pagination |
| `/nv-phuc-vu` | POST | Create Service Staff |
| `/nv-phuc-vu` | GET | Get all Service Staff |
| `/nv-phuc-vu/{id}` | GET | Get Service Staff by ID |
| `/nv-phuc-vu/{id}` | PUT | Update Service Staff |
| `/nv-phuc-vu/{id}` | DELETE | Delete Service Staff |
| `/nv-phuc-vu/search` | POST | Search Service Staff with pagination |
| `/nv-thu-ngan` | POST | Create Cashier Staff |
| `/nv-thu-ngan` | GET | Get all Cashier Staff |
| `/nv-thu-ngan/{id}` | GET | Get Cashier Staff by ID |
| `/nv-thu-ngan/{id}` | PUT | Update Cashier Staff |
| `/nv-thu-ngan/{id}` | DELETE | Delete Cashier Staff |
| `/nv-thu-ngan/search` | POST | Search Cashier Staff with pagination |
| `/phieu-nhap` | POST | Create Purchase Order with details |
| `/phieu-nhap` | GET | Get all Purchase Orders |
| `/phieu-nhap/{id}` | GET | Get Purchase Order by ID |
| `/phieu-nhap/{id}` | PUT | Update Purchase Order (incl. details) |
| `/phieu-nhap/{id}` | DELETE | Delete Purchase Order |
| `/phieu-nhap/search` | POST | Search Purchase Orders with pagination |

---

## 💡 Postman Quick Examples

### Create NvKhoBep
```
POST http://localhost:8080/api/nv-kho-bep
Content-Type: application/json

{
  "maNhanVien": "NV001",
  "viTri": "Bếp Chính",
  "trinhDo": "Đầu bếp"
}
```

### Update NvKhoBep
```
PUT http://localhost:8080/api/nv-kho-bep/NV001
Content-Type: application/json

{
  "maNhanVien": "NV001",
  "viTri": "Bếp Hỗ Trợ",
  "trinhDo": "Phụ bếp"
}
```

### Search NvKhoBep
```
POST http://localhost:8080/api/nv-kho-bep/search
Content-Type: application/json

{
  "q": "Bếp",
  "page": 0,
  "size": 20,
  "sortBy": "maNhanVien",
  "sortDir": "asc"
}
```

### Create NvPhucVu
```
POST http://localhost:8080/api/nv-phuc-vu
Content-Type: application/json

{
  "maNhanVien": "NV002",
  "khuVucPhuTrach": "Khu A",
  "soLuongBanPhucVu": 5
}
```

### Create NvThuNgan
```
POST http://localhost:8080/api/nv-thu-ngan
Content-Type: application/json

{
  "maNhanVien": "NV003",
  "caThuNgan": "Ca Sáng",
  "tongTienXuLy": 5000000.00
}
```

### Create PhieuNhap with Details
```
POST http://localhost:8080/api/phieu-nhap
Content-Type: application/json

{
  "maNcc": "NCC001",
  "maNhanVien": "NV001",
  "ngayNhap": "2026-03-02T10:00:00",
  "chiTietList": [
    {
      "maNguyenLieu": "NL001",
      "soLuong": 10,
      "donGia": 50000.00
    },
    {
      "maNguyenLieu": "NL002",
      "soLuong": 20,
      "donGia": 75000.00
    }
  ]
}
```

### Update PhieuNhap (Update details)
```
PUT http://localhost:8080/api/phieu-nhap/PN-123456789
Content-Type: application/json

{
  "maNcc": "NCC001",
  "maNhanVien": "NV001",
  "ngayNhap": "2026-03-02T11:00:00",
  "chiTietList": [
    {
      "maNguyenLieu": "NL003",
      "soLuong": 5,
      "donGia": 100000.00
    }
  ]
}
```

---

## ⚠️ Common Validation Errors

### Missing Required Field
```json
{
  "success": false,
  "message": "Validation error",
  "data": {
    "maNhanVien": "maNhanVien is required"
  },
  "timestamp": "2026-03-02T10:30:00"
}
```

**Fix**: Provide all required fields

### Invalid Reference (NhanVien doesn't exist)
```json
{
  "success": false,
  "message": "NhanVien not found: NV999",
  "timestamp": "2026-03-02T10:30:00"
}
```

**Fix**: Make sure the employee exists in the database first

### Entity Not Found
```json
{
  "success": false,
  "message": "Không tìm thấy entity: NV001",
  "timestamp": "2026-03-02T10:30:00"
}
```

**Fix**: Use a valid ID that exists

### Invalid Numeric Value
```json
{
  "success": false,
  "message": "Validation error",
  "data": {
    "soLuong": "soLuong must be at least 1"
  },
  "timestamp": "2026-03-02T10:30:00"
}
```

**Fix**: Provide valid numeric values (soLuong >= 1, tongTienXuLy >= 0)

---

## 📋 Search Parameters

### Common Parameters for All Search Endpoints
```json
{
  "q": "search query",              // Optional: search keyword (LIKE query)
  "page": 0,                        // Optional: page number (0-indexed, default: 0)
  "size": 20,                       // Optional: page size (default: 20)
  "sortBy": "fieldName",            // Optional: sort field (default: "id")
  "sortDir": "asc"                  // Optional: "asc" or "desc" (default: "asc")
}
```

### Example Searches

**Search by Keyword**:
```json
{
  "q": "Bếp",
  "page": 0,
  "size": 10
}
```
Returns all NvKhoBep where viTri or trinhDo contains "Bếp"

**Sort by Field**:
```json
{
  "sortBy": "soLuongBanPhucVu",
  "sortDir": "desc",
  "page": 0,
  "size": 20
}
```
Returns NvPhucVu sorted by soLuongBanPhucVu in descending order

---

## 🔍 Common Test Scenarios

### Scenario 1: Create Complete PhieuNhap Flow

1. **Create NhanVien** (if not exists):
```
POST /api/nhan-vien
{ "maNhanVien": "NV001", ... }
```

2. **Create NhaCungCap** (if not exists):
```
POST /api/nha-cung-cap
{ "maNcc": "NCC001", ... }
```

3. **Create NguyenLieu** (if not exists):
```
POST /api/nguyen-lieu
{ "maNguyenLieu": "NL001", ... }
```

4. **Create PhieuNhap**:
```
POST /api/phieu-nhap
{
  "maNcc": "NCC001",
  "maNhanVien": "NV001",
  "ngayNhap": "2026-03-02T10:00:00",
  "chiTietList": [
    {
      "maNguyenLieu": "NL001",
      "soLuong": 10,
      "donGia": 50000.00
    }
  ]
}
```

---

### Scenario 2: Create and Update NvKhoBep

1. **Create**:
```
POST /api/nv-kho-bep
{
  "maNhanVien": "NV001",
  "viTri": "Bếp Chính",
  "trinhDo": "Đầu bếp"
}
```

2. **Update**:
```
PUT /api/nv-kho-bep/NV001
{
  "maNhanVien": "NV001",
  "viTri": "Bếp Hỗ Trợ",
  "trinhDo": "Phụ bếp"
}
```

3. **Get**:
```
GET /api/nv-kho-bep/NV001
```

4. **Search**:
```
POST /api/nv-kho-bep/search
{
  "q": "Hỗ Trợ",
  "page": 0,
  "size": 10
}
```

5. **Delete**:
```
DELETE /api/nv-kho-bep/NV001
```

---

## 🛠️ Troubleshooting

### Port Already in Use
```
Port 8080 is already in use
```
**Solution**: 
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9
# Or use different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Database Connection Error
```
Cannot connect to database
```
**Solution**: 
- Check database is running
- Verify connection parameters in `application.yml` or `application.properties`
- Ensure database user has correct privileges

### Validation Not Working
**Solution**:
- Ensure `@Validated` annotation is on controller method
- Ensure `@Valid` annotation is on request body parameter
- Check validation annotations are imported from `javax.validation`

### PhieuNhap Update Not Replacing Details
**Solution**:
- Current implementation deletes existing details and inserts new ones
- Make sure all nguyenLieu IDs in chiTietList exist
- Check that transaction is properly committed

---

## 📞 Support Info

### Key Classes to Review
- `GlobalExceptionHandler.java` - Error handling
- `AbstractCatalogController.java` - Common endpoints
- `AbstractCatalogService.java` - Common logic
- `CatalogServiceHelper.java` - Search/pagination helper

### Documentation Files
- `NV_ROLES_API_DOCUMENTATION.md` - Complete API reference
- `IMPLEMENTATION_SUMMARY.md` - Architecture and implementation details
- `QUICK_REFERENCE.md` - This file

---

## 📊 Entity Relationships

```
nhan_vien (1)
    ↑
    ├─── nv_kho_bep (0..1)
    ├─── nv_phuc_vu (0..1)
    └─── nv_thu_ngan (0..1)

phieu_nhap (1) ──┬─── nha_cung_cap (1)
                 ├─── nhan_vien (1)
                 └─── chi_tiet_phieu_nhap (*)
                        └─── nguyen_lieu (1)
```

---

## ✅ Checklist for New NV Role APIs

When creating a new role API following the same pattern:

- [ ] Create Request DTO with validation annotations
- [ ] Create Response DTO
- [ ] Create SearchRequest extending BaseSearchRequest
- [ ] Create Service interface extending CatalogServiceContract
- [ ] Create ServiceImpl extending AbstractCatalogService
- [ ] Implement all abstract methods
- [ ] Add foreign key validation in createEntity()
- [ ] Create Controller extending AbstractCatalogController
- [ ] Update Repository to extend JpaSpecificationExecutor
- [ ] Add @RequestMapping annotation to controller
- [ ] Test all CRUD endpoints
- [ ] Test search endpoint with pagination
- [ ] Test validation errors
- [ ] Test foreign key reference errors

