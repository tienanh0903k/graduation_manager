import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Dashboard } from './app/pages/dashboard/dashboard';
import { Documentation } from './app/pages/documentation/documentation';
import { Landing } from './app/pages/landing/landing';
import { Notfound } from './app/pages/notfound/notfound';
import { AuthGuard } from './app/core/guards/auth.guard';
import { ReportComponent } from './app/pages/report/report.component';
import { CreateProjectComponent } from './app/pages/create-project/create-project.component';
import { ManageProjectComponent } from './app/pages/manage-project/manage-project.component';
import { MyProjectComponent } from './app/pages/my-project/my-project.component';
import { ViewCouncilComponent } from './app/pages/view-council/view-council.component';
import { ReviewProjectComponent } from './app/pages/admin-teacher/review-project/review-project.component';
import { ListProjectComponent } from './app/pages/admin-teacher/list-project/list-project.component';
import { StudentReviewComponent } from './app/pages/admin-teacher/student-review/student-review.component';
import { RoleComponent } from './app/pages/admin-system/role/role.component';
import { StudentsComponent } from './app/pages/admin-system/students/students.component';
import { ProfilePageComponent } from './app/pages/profile-page/profile-page.component';

// ✅ Dùng RoleGuard thay vì AdminGuard + TeacherGuard riêng
import { RoleGuard } from './app/core/guards/role.guard';
import { MenusComponent } from './app/pages/admin-system/menus/menus.component';

export const appRoutes: Routes = [
  {
    path: '',
    component: AppLayout,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: Dashboard },
      { path: 'profile', component: ProfilePageComponent },
      { path: 'report', component: ReportComponent },
      { path: 'create-project', component: CreateProjectComponent },
      { path: 'manage-project', component: ManageProjectComponent },
      { path: 'my-project', component: MyProjectComponent },
      { path: 'view-council', component: ViewCouncilComponent },
      { path: 'documentation', component: Documentation },

      // 📘 Route dành cho giáo viên
      {
        path: 'review-project',
        component: ReviewProjectComponent,
        canActivate: [RoleGuard],
        data: { role: 'TEACHER' }
      },
      {
        path: 'approve-project',
        component: ListProjectComponent,
        canActivate: [RoleGuard],
        data: { role: 'TEACHER' }
      },
      {
        path: 'teacher-review',
        component: StudentReviewComponent,
        canActivate: [RoleGuard],
        data: { role: 'TEACHER' }
      },

      // 📘 Route dành cho admin
      {
        path: 'student-management',
        component: StudentsComponent,
        canActivate: [RoleGuard],
        data: { role: 'ADMIN' }
      },
      {
        path: 'role-management',
        component: RoleComponent,
        canActivate: [RoleGuard],
        data: { role: 'ADMIN' }
      },
      {
        path: 'menu-management',
        component: MenusComponent,
        canActivate: [RoleGuard],
        data: { role: 'ADMIN' }
      },

      // 📁 UI demo (nếu có)
      {
        path: 'uikit',
        loadChildren: () => import('./app/pages/uikit/uikit.routes')
      }
    ]
  },

  // 📄 Trang độc lập
  { path: 'landing', component: Landing },
  { path: 'notfound', component: Notfound },
  {
    path: 'auth',
    loadChildren: () => import('./app/pages/auth/auth.routes')
  },
  { path: '**', redirectTo: '/notfound' }
];
