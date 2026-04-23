import api from "../api";
import { useForm } from "../hooks/useForm";
import { FormField } from "../components/FormField";
import { validateXe } from "../validations/form";

export default function XePage() {
  const {
    values,
    isSubmitting,
    submitError,
    handleChange,
    handleBlur,
    handleSubmit,
    getFieldError,
    setFieldValue,
    hasErrors,
  } = useForm({
    initialValues: {
      maXe: "",
      bienSo: "",
      hanKiemDinh: "",
    },
    validate: validateXe,
    normalize: {
      bienSo: (v: string) => v.toUpperCase(),
    },
    onSubmit: async (data) => {
      await api.post("/api/xe", data);
      alert("Thành công");
    },
  });

  return (
    <form onSubmit={handleSubmit}>
      {submitError && <div style={{ color: "red" }}>{submitError}</div>}

      <FormField
        label="Mã xe"
        name="maXe"
        value={values.maXe}
        onChange={handleChange}
        onBlur={handleBlur}
        error={getFieldError("maXe")}
      />

      <FormField
        label="Biển số"
        name="bienSo"
        value={values.bienSo}
        onChange={(e: any) => setFieldValue("bienSo", e.target.value)}
        onBlur={handleBlur}
        error={getFieldError("bienSo")}
      />

      <FormField
        label="Hạn kiểm định"
        type="date"
        name="hanKiemDinh"
        value={values.hanKiemDinh}
        onChange={handleChange}
        onBlur={handleBlur}
        error={getFieldError("hanKiemDinh")}
      />

      <button disabled={isSubmitting || hasErrors}>
        {isSubmitting ? "Đang lưu..." : "Lưu"}
      </button>
    </form>
  );
}