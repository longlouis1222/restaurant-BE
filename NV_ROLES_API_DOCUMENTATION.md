# API Documentation - NV Roles (Nhân Viên Roles)

## Overview
This document provides API endpoints and Postman test examples for managing 3 NV specialized roles:
- **NvKhoBep** (Kitchen Staff) - `/api/nv-kho-bep`
- **NvPhucVu** (Service Staff) - `/api/nv-phuc-vu`
- **NvThuNgan** (Cashier Staff) - `/api/nv-thu-ngan`

All endpoints follow the standard CRUD + Search pattern with validation.

---

## 1. NvKhoBep (Kitchen Staff) Endpoints

### Base URL: `/api/nv-kho-bep`

#### Create NvKhoBep
**POST** `/api/nv-kho-bep`

Request Body:
```json
{
  "maNhanVien": "NV001",
  "viTri": "Bếp Chính",
  "trinhDo": "Đầu bếp"
}
```

Success Response (201):
```json
{
  "success": true,
  "message": "Tạo thành công",
  "data": {
    "maNhanVien": "NV001",
    "viTri": "Bếp Chính",
    "trinhDo": "Đầu bếp"
  }
}
```

#### Update NvKhoBep
**PUT** `/api/nv-kho-bep/{id}`

Request Body:
```json
{
  "maNhanVien": "NV001",
  "viTri": "Bếp Hỗ Trợ",
  "trinhDo": "Phụ bếp"
}
```

Success Response (200):
```json
{
  "success": true,
  "message": "Cập nhật thành công",
  "data": {
    "maNhanVien": "NV001",
    "viTri": "Bếp Hỗ Trợ",
    "trinhDo": "Phụ bếp"
  }
}
```

#### Get NvKhoBep by ID
**GET** `/api/nv-kho-bep/{id}`

Success Response (200):
```json
{
  "success": true,
  "data": {
    "maNhanVien": "NV001",
    "viTri": "Bếp Hỗ Trợ",
    "trinhDo": "Phụ bếp"
  }
}
```

#### Get All NvKhoBep
**GET** `/api/nv-kho-bep`

Success Response (200):
```json
{
  "success": true,
  "data": [
    {
      "maNhanVien": "NV001",
      "viTri": "Bếp Chính",
      "trinhDo": "Đầu bếp"
    }
  ]
}
```

#### Search NvKhoBep with Pagination
**POST** `/api/nv-kho-bep/search`

Request Body:
```json
{
  "q": "Bếp",
  "page": 0,
  "size": 20,
  "sortBy": "maNhanVien",
  "sortDir": "asc"
}
```

Success Response (200):
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "maNhanVien": "NV001",
        "viTri": "Bếp Chính",
        "trinhDo": "Đầu bếp"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

#### Delete NvKhoBep
**DELETE** `/api/nv-kho-bep/{id}`

Success Response (200):
```json
{
  "success": true,
  "message": "Xóa thành công"
}
```

#### Validation Error Example
**POST** `/api/nv-kho-bep` (with invalid data)

Request Body:
```json
{
  "maNhanVien": "",
  "viTri": "Bếp Chính",
  "trinhDo": "Đầu bếp"
}
```

Error Response (400):
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

---

## 2. NvPhucVu (Service Staff) Endpoints

### Base URL: `/api/nv-phuc-vu`

#### Create NvPhucVu
**POST** `/api/nv-phuc-vu`

Request Body:
```json
{
  "maNhanVien": "NV002",
  "khuVucPhuTrach": "Khu A",
  "soLuongBanPhucVu": 5
}
```

Success Response (201):
```json
{
  "success": true,
  "message": "Tạo thành công",
  "data": {
    "maNhanVien": "NV002",
    "khuVucPhuTrach": "Khu A",
    "soLuongBanPhucVu": 5
  }
}
```

#### Update NvPhucVu
**PUT** `/api/nv-phuc-vu/{id}`

Request Body:
```json
{
  "maNhanVien": "NV002",
  "khuVucPhuTrach": "Khu B",
  "soLuongBanPhucVu": 8
}
```

Success Response (200):
```json
{
  "success": true,
  "message": "Cập nhật thành công",
  "data": {
    "maNhanVien": "NV002",
    "khuVucPhuTrach": "Khu B",
    "soLuongBanPhucVu": 8
  }
}
```

#### Get NvPhucVu by ID
**GET** `/api/nv-phuc-vu/{id}`

#### Get All NvPhucVu
**GET** `/api/nv-phuc-vu`

#### Search NvPhucVu with Pagination
**POST** `/api/nv-phuc-vu/search`

Request Body:
```json
{
  "q": "Khu",
  "page": 0,
  "size": 20,
  "sortBy": "khuVucPhuTrach",
  "sortDir": "asc"
}
```

#### Delete NvPhucVu
**DELETE** `/api/nv-phuc-vu/{id}`

---

## 3. NvThuNgan (Cashier Staff) Endpoints

### Base URL: `/api/nv-thu-ngan`

#### Create NvThuNgan
**POST** `/api/nv-thu-ngan`

Request Body:
```json
{
  "maNhanVien": "NV003",
  "caThuNgan": "Ca Sáng",
  "tongTienXuLy": 5000000.00
}
```

Success Response (201):
```json
{
  "success": true,
  "message": "Tạo thành công",
  "data": {
    "maNhanVien": "NV003",
    "caThuNgan": "Ca Sáng",
    "tongTienXuLy": 5000000.00
  }
}
```

#### Update NvThuNgan
**PUT** `/api/nv-thu-ngan/{id}`

Request Body:
```json
{
  "maNhanVien": "NV003",
  "caThuNgan": "Ca Chiều",
  "tongTienXuLy": 6500000.00
}
```

Success Response (200):
```json
{
  "success": true,
  "message": "Cập nhật thành công",
  "data": {
    "maNhanVien": "NV003",
    "caThuNgan": "Ca Chiều",
    "tongTienXuLy": 6500000.00
  }
}
```

#### Get NvThuNgan by ID
**GET** `/api/nv-thu-ngan/{id}`

#### Get All NvThuNgan
**GET** `/api/nv-thu-ngan`

#### Search NvThuNgan with Pagination
**POST** `/api/nv-thu-ngan/search`

Request Body:
```json
{
  "q": "Ca Sáng",
  "page": 0,
  "size": 20,
  "sortBy": "caThuNgan",
  "sortDir": "asc"
}
```

#### Delete NvThuNgan
**DELETE** `/api/nv-thu-ngan/{id}`

---

## Validation Rules

### NvKhoBepRequest
- `maNhanVien`: Required (must be blank), must exist in NhanVien table
- `viTri`: Required, non-blank
- `trinhDo`: Required, non-blank

### NvPhucVuRequest
- `maNhanVien`: Required, must exist in NhanVien table
- `khuVucPhuTrach`: Required, non-blank
- `soLuongBanPhucVu`: Required, >= 0

### NvThuNganRequest
- `maNhanVien`: Required, must exist in NhanVien table
- `caThuNgan`: Required, non-blank
- `tongTienXuLy`: Required, >= 0 (BigDecimal)

---

## Error Responses

### 404 Not Found
```json
{
  "success": false,
  "message": "Không tìm thấy entity: NV001",
  "timestamp": "2026-03-02T10:30:00"
}
```

### 400 Bad Request (Validation Error)
```json
{
  "success": false,
  "message": "Validation error",
  "data": {
    "fieldName": "validation message"
  },
  "timestamp": "2026-03-02T10:30:00"
}
```

### 400 Bad Request (Foreign Key Error)
```json
{
  "success": false,
  "message": "NhanVien not found: NV999",
  "timestamp": "2026-03-02T10:30:00"
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Internal Server Error",
  "timestamp": "2026-03-02T10:30:00"
}
```

---

## Notes

1. **Authentication**: These endpoints are currently open (no authentication required). Consider adding Spring Security if needed.

2. **Pagination**: Default page size is 20. Use `page` (0-indexed) and `size` parameters in search requests.

3. **Search**: The search uses LIKE query on specified fields. Query is case-insensitive.

4. **Sorting**: Default sort is by `id` ascending. Use `sortBy` and `sortDir` (asc/desc) for custom sorting.

5. **Foreign Key Validation**: All NV roles require a valid `maNhanVien` that exists in the `nhan_vien` table. An error will be returned if the employee doesn't exist.

6. **Date/Time**: `timestamp` fields use LocalDateTime format (ISO 8601).

---

## Example Postman Collection Structure

```
NV Roles API
├── NvKhoBep
│   ├── Create NvKhoBep (POST)
│   ├── Get All NvKhoBep (GET)
│   ├── Get NvKhoBep by ID (GET)
│   ├── Update NvKhoBep (PUT)
│   ├── Delete NvKhoBep (DELETE)
│   └── Search NvKhoBep (POST)
├── NvPhucVu
│   ├── Create NvPhucVu (POST)
│   ├── Get All NvPhucVu (GET)
│   ├── Get NvPhucVu by ID (GET)
│   ├── Update NvPhucVu (PUT)
│   ├── Delete NvPhucVu (DELETE)
│   └── Search NvPhucVu (POST)
└── NvThuNgan
    ├── Create NvThuNgan (POST)
    ├── Get All NvThuNgan (GET)
    ├── Get NvThuNgan by ID (GET)
    ├── Update NvThuNgan (PUT)
    ├── Delete NvThuNgan (DELETE)
    └── Search NvThuNgan (POST)
```

---

## Implementation Summary

### Files Created/Modified:

**NvKhoBep**:
- `NvKhoBepRequest.java` - Request DTO with validation
- `NvKhoBepResponse.java` - Response DTO
- `NvKhoBepSearchRequest.java` - Search request extending BaseSearchRequest
- `NvKhoBepService.java` - Service interface
- `NvKhoBepServiceImpl.java` - Service implementation
- `NvKhoBepController.java` - REST Controller
- `NvKhoBepRepository.java` - Modified to extend JpaSpecificationExecutor

**NvPhucVu**:
- `NvPhucVuRequest.java` - Request DTO with validation
- `NvPhucVuResponse.java` - Response DTO
- `NvPhucVuSearchRequest.java` - Search request extending BaseSearchRequest
- `NvPhucVuService.java` - Service interface
- `NvPhucVuServiceImpl.java` - Service implementation
- `NvPhucVuController.java` - REST Controller
- `NvPhucVuRepository.java` - Modified to extend JpaSpecificationExecutor

**NvThuNgan**:
- `NvThuNganRequest.java` - Request DTO with validation
- `NvThuNganResponse.java` - Response DTO
- `NvThuNganSearchRequest.java` - Search request extending BaseSearchRequest
- `NvThuNganService.java` - Service interface
- `NvThuNganServiceImpl.java` - Service implementation
- `NvThuNganController.java` - REST Controller
- `NvThuNganRepository.java` - Modified to extend JpaSpecificationExecutor

### Features Included:

✅ Full CRUD operations (Create, Read, Update, Delete)
✅ Pagination and search with filter
✅ Input validation with custom error messages
✅ Foreign key validation (NhanVien existence check)
✅ Exception handling with unified ApiResponse format
✅ Transaction management (@Transactional)
✅ RESTful API design patterns
✅ Generic controller/service architecture for code reuse

