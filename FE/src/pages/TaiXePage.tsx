import { useForm } from "../hooks/useForm";
import { validateTaiXe } from "../validations/form";

export default function TaiXePage() {
  const { values, handleChange, handleSubmit, getFieldError } = useForm({
    initialValues: { tenTaiXe: "", soDienThoai: "" },
    validate: validateTaiXe,
    onSubmit: (data) => console.log(data),
  });

  return (
    <form onSubmit={handleSubmit}>
      <input name="tenTaiXe" onChange={handleChange} />
      <p>{getFieldError("tenTaiXe")}</p>

      <input name="soDienThoai" onChange={handleChange} />
      <p>{getFieldError("soDienThoai")}</p>

      <button>Submit</button>
    </form>
  );
}