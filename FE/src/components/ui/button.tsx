import * as React from "react"
import { cn } from "../../lib/utils"

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "default" | "destructive" | "outline" | "secondary" | "ghost" | "link"
  size?: "default" | "sm" | "lg" | "icon"
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant = "default", size = "default", ...props }, ref) => {
    return (
      <button
        ref={ref}
        className={cn(
          "inline-flex items-center justify-center whitespace-nowrap rounded-[10px] text-[13px] font-bold ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-[#003366]/20 focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50",
          {
            "bg-[#003366] text-white hover:bg-[#003366]/90 px-3 py-[12px]": variant === "default",
            "bg-[#EE5D50] text-white hover:bg-[#EE5D50]/90 px-3 py-[12px]": variant === "destructive",
            "border border-[#E0E5F2] bg-transparent text-[#2B3674] hover:bg-slate-50 px-3 py-[6px] rounded-[8px] text-[12px]": variant === "outline",
            "bg-slate-100 text-[#2B3674] hover:bg-slate-200 px-3 py-[12px]": variant === "secondary",
            "hover:bg-slate-100 hover:text-[#2B3674]": variant === "ghost",
            "text-[#003366] underline-offset-4 hover:underline": variant === "link",
          },
          className
        )}
        {...props}
      />
    )
  }
)
Button.displayName = "Button"

export { Button }
