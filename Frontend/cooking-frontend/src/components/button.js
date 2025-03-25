
// Button Component
export function Button({ children, onClick, className }) {
  return (
    <button
      onClick={onClick}
      className={`px-4 py-2 bg-blue-500 text-black rounded hover:bg-blue-700 ${className}`}
    >
      {children}
    </button>
  );
}