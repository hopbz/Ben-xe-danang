import * as React from "react"
import { cn } from "../../lib/utils"

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, ...props }, ref) => {
    return (
      <input
        type={type}
        className={cn(
          "flex w-full rounded-[8px] border border-[#E0E5F2] bg-white px-[12px] py-[10px] text-[13px] text-[#2B3674] file:border-0 file:bg-transparent file:text-[13px] file:font-medium placeholder:text-[#A3AED0] focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-[#003366] disabled:cursor-not-allowed disabled:opacity-50",
          className
        )}
        ref={ref}
        {...props}
      />
    )
  }
)
Input.displayName = "Input"

export { Input }
