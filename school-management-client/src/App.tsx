import { Routes, Route, Link } from "react-router-dom";
import StudentsPage from "./pages/StudentsPage";
import TeachersPage from "./pages/TeachersPage"; // You'll create this later
import HomePage from "./pages/HomePage"; // You'll create this later
import { Button } from "@/components/ui/button";
function App() {
  return (
    <div>
      <nav>
        <Link to="/">Home</Link> | <Link to="/students">Students</Link> |{" "}
        <Link to="/teachers">Teachers</Link>
      </nav>
      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/students" element={<StudentsPage />} />
          <Route path="/teachers" element={<TeachersPage />} />
        </Routes>
      </main>
      <div className="flex min-h-svh flex-col items-center justify-center">
        <Button>Click me</Button>
      </div>
    </div>
  );
}

export default App;
