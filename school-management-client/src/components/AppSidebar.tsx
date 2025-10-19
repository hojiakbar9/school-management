import React from "react";
import {
  Sidebar,
  SidebarContent,
  SidebarHeader,
  SidebarMenuButton,
} from "./ui/sidebar";
import { GiArabicDoor } from "react-icons/gi";
import NavManagement from "./NavManagement";
import NavDashboard from "./NavDashboard";

const AppSidebar = ({ ...props }: React.ComponentProps<typeof Sidebar>) => {
  return (
    <>
      <Sidebar collapsible="icon" {...props}>
        <SidebarHeader>
          <SidebarMenuButton
            size="lg"
            className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
          >
            <div className=" flex aspect-square size-8 items-center justify-center rounded-lg">
              <span className="text-4xl">
                <GiArabicDoor />
              </span>
            </div>
            <div className="grid flex-1 text-left text-sm leading-tight">
              <span className="truncate font-medium">Arabische Schule</span>
              <span className="truncate text-xs">Marburger Moschee</span>
            </div>
          </SidebarMenuButton>
        </SidebarHeader>
        <SidebarContent className="my-3">
          <NavDashboard />
          <NavManagement />
        </SidebarContent>
      </Sidebar>
    </>
  );
};

export default AppSidebar;
