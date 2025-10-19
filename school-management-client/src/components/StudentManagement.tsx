import { students } from "@/data/students";
import { studentColumns } from "./columns";
import React from "react";
import DataTable from "./DataTable";

const StudentManagement = () => {
  const data = students;

  return (
    <div className="container mx-auto py-10">
      <h2 className="text-3xl font-bold mb-4">Shülerliste</h2>
      <DataTable columns={studentColumns} data={data} />
    </div>
  );
};

export default StudentManagement;
