import { FormErrors } from "../hooks/useForm";

const bienSoRegex = /^\d\d[A-Z]\d-\d{5}$/;
const phoneRegex = /^(0[3|5|7|8|9])[0-9]{8}$/;

export function validateXe(values: any): FormErrors<any> {
  const errors: any = {};

  if (!values.maXe) errors.maXe = "Không được để trống";

  if (!values.bienSo) {
    errors.bienSo = "Không được để trống";
  } else if (!bienSoRegex.test(values.bienSo)) {
    errors.bienSo = "Sai định dạng xxYx-xxxxx";
  }

  if (!values.hanKiemDinh) {
    errors.hanKiemDinh = "Không được để trống";
  } else {
    const now = new Date();
    const min = new Date();
    min.setMonth(now.getMonth() + 1);

    if (new Date(values.hanKiemDinh) <= min) {
      errors.hanKiemDinh = "Phải > hiện tại 1 tháng";
    }
  }

  return errors;
}

export function validateNhaXe(values: any): FormErrors<any> {
  const errors: any = {};

  if (!values.tenNhaXe) errors.tenNhaXe = "Không được để trống";

  const year = Number(values.namThanhLap);
  const now = new Date().getFullYear();

  if (!year || year < 1900 || year > now) {
    errors.namThanhLap = "Năm không hợp lệ";
  }

  return errors;
}

export function validateTuyen(values: any): FormErrors<any> {
  const errors: any = {};

  if (!values.tenTuyen) errors.tenTuyen = "Không được để trống";

  if (!values.donGia || Number(values.donGia) <= 0) {
    errors.donGia = "Phải > 0";
  }

  return errors;
}

export function validateTaiXe(values: any): FormErrors<any> {
  const errors: any = {};

  if (!values.tenTaiXe || values.tenTaiXe.length < 3) {
    errors.tenTaiXe = ">= 3 ký tự";
  }

  if (!phoneRegex.test(values.soDienThoai || "")) {
    errors.soDienThoai = "SĐT không hợp lệ";
  }

  return errors;
}