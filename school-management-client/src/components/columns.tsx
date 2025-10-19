import type { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
type Person = {
  id: number;
  firstName: string;
  secondName: string;
  phoneNumber: string;
};

type Student = Person;
type Teacher = Person;

const personColumns: ColumnDef<Person>[] = [
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "firstName",
    header: "Vorname",
  },
  {
    accessorKey: "secondName",
    header: "Nachname",
  },
  {
    accessorKey: "phoneNumber",
    header: "Handy",
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const student = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Menu öffnen</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Aktionen</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() =>
                navigator.clipboard.writeText(student.id.toString())
              }
            >
              ID kopieren
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem>Profil ansehen</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  },
];

export const studentColumns = personColumns as ColumnDef<Student>[];
export const teacherColumns = personColumns as ColumnDef<Student>[];
