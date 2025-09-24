import axios from "axios";

const apiClient = axios.create({
  baseURL: "/api", // This uses the Vite proxy
  headers: {
    "Content-Type": "application/json",
  },
});

// Student API calls
export const getStudents = () => apiClient.get("/students");
export const getStudentById = (id: number) => apiClient.get(`/students/${id}`);
