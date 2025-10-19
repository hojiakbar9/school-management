import React from "react";
import { PiStudentBold } from "react-icons/pi";
import { FaChalkboardTeacher } from "react-icons/fa";
import { SiGoogleclassroom } from "react-icons/si";
import {
  SidebarGroup,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuItem,
  SidebarMenuButton,
} from "./ui/sidebar";

const menuItems = [
  {
    label: "Schülerverwaltung",
    icon: <PiStudentBold />,
  },
  {
    label: "Lehrerverwaltung",
    icon: <FaChalkboardTeacher />,
  },
  {
    label: "Klassenverwaltung",
    icon: <SiGoogleclassroom />,
  },
];

const NavManagement = () => {
  return (
    <SidebarGroup>
      <SidebarGroupLabel>Verwaltung</SidebarGroupLabel>
      <SidebarMenu>
        {menuItems.map(({ label, icon }) => (
          <SidebarMenuItem key={label}>
            <SidebarMenuButton asChild>
              <span className="flex items-center gap-2">
                {icon}
                {label}
              </span>
            </SidebarMenuButton>
          </SidebarMenuItem>
        ))}
      </SidebarMenu>
    </SidebarGroup>
  );
};

export default NavManagement;
