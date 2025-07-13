//
// src/app/constants/menu.config.ts
export const MENU_CONFIG = [
  {
    label: 'Dashboard',
    icon: 'pi pi-home',
    route: '/dashboard',
    children: [],
  },
  {
    label: 'Quản lý Sinh viên',
    icon: 'pi pi-user',
    route: '/students',
    children: [],
  },
  {
    label: 'Quản lý Đồ án',
    icon: 'pi pi-briefcase',
    route: '/projects',
    children: [],
  }
];
export const departments = Array.from({length: 9}, (_, i) => {
    const val = (125211 + i).toString();
    return { label: val, value: val };
});
