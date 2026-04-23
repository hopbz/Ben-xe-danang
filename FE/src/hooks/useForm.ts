import { useCallback, useMemo, useState } from "react";

type FieldKey<T> = Extract<keyof T, string>;
export type FormErrors<T> = Partial<Record<FieldKey<T>, string>>;
export type FormTouched<T> = Partial<Record<FieldKey<T>, boolean>>;

export type Validator<T> = (values: T) => FormErrors<T>;

type UseFormOptions<T extends Record<string, any>> = {
  initialValues: T;
  validate?: Validator<T>;
  onSubmit: (values: T) => Promise<void> | void;
  normalize?: Partial<Record<FieldKey<T>, (value: string, values: T) => string>>;
  validateOnChange?: boolean;
  validateOnBlur?: boolean;
};

export function useForm<T extends Record<string, any>>({
  initialValues,
  validate,
  onSubmit,
  normalize,
  validateOnChange = true,
  validateOnBlur = true,
}: UseFormOptions<T>) {
  const [values, setValues] = useState<T>(initialValues);
  const [errors, setErrors] = useState<FormErrors<T>>({});
  const [touched, setTouched] = useState<FormTouched<T>>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState("");
  const [submitCount, setSubmitCount] = useState(0);

  const validateAll = useCallback(
    (currentValues: T) => {
      if (!validate) return {} as FormErrors<T>;
      return validate(currentValues);
    },
    [validate]
  );

  const setFieldValue = useCallback(
    (field: FieldKey<T>, rawValue: string) => {
      setValues((prev) => {
        const nextValue = normalize?.[field]?.(rawValue, prev) ?? rawValue;
        const nextValues = { ...prev, [field]: nextValue } as T;

        if (validateOnChange && validate) {
          setErrors(validateAll(nextValues));
        }

        return nextValues;
      });
    },
    [normalize, validateOnChange, validate, validateAll]
  );

  const handleChange = useCallback((e: any) => {
    const { name, value } = e.target;
    setFieldValue(name, value);
  }, [setFieldValue]);

  const handleBlur = useCallback((e: any) => {
    const field = e.target.name;
    setTouched((prev) => ({ ...prev, [field]: true }));

    if (validateOnBlur && validate) {
      setErrors(validateAll(values));
    }
  }, [validateOnBlur, validate, validateAll, values]);

  const getFieldError = (field: FieldKey<T>) => {
    const shouldShow = touched[field] || submitCount > 0;
    return shouldShow ? errors[field] : "";
  };

  const hasErrors = useMemo(() => Object.keys(errors).length > 0, [errors]);

  const resetForm = () => {
    setValues(initialValues);
    setErrors({});
    setTouched({});
    setSubmitError("");
    setSubmitCount(0);
  };

  const handleSubmit = async (e?: any) => {
    e?.preventDefault();
    setSubmitCount((c) => c + 1);

    const nextErrors = validate ? validateAll(values) : {};
    setErrors(nextErrors);

    if (Object.keys(nextErrors).length > 0) return;

    try {
      setIsSubmitting(true);
      setSubmitError("");
      await onSubmit(values);
    } catch (err: any) {
      setSubmitError(err?.response?.data?.message || "Lỗi server");
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    values,
    errors,
    touched,
    isSubmitting,
    submitError,
    hasErrors,
    handleChange,
    handleBlur,
    handleSubmit,
    getFieldError,
    setFieldValue,
    resetForm,
  };
}