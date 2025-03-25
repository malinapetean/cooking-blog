// Card Component
export function Card({ children, className }) {
  return <div className={`border rounded-lg p-4 shadow-md ${className}`}>{children}</div>;
}