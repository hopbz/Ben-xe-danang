export function FormField({ label, error, ...props }: any) {
  return (
    <div style={{ marginBottom: 10 }}>
      <label>{label}</label>
      <input {...props} style={{ display: "block", width: "100%" }} />
      {error && <div style={{ color: "red" }}>{error}</div>}
    </div>
  );
}