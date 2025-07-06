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
import { TeacherGuard } from './app/core/guards/teacher.guard';
import { ReviewProjectComponent } from './app/pages/admin-teacher/review-project/review-project.component';
import { ListProjectComponent } from './app/pages/admin-teacher/list-project/list-project.component';


export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        canActivate: [AuthGuard],
        children: [
            { path: '', component: Dashboard },
            { path: 'report', component: ReportComponent },
            { path: 'create-project', component: CreateProjectComponent },
            { path: 'manage-project', component: ManageProjectComponent },
            { path: 'my-project', component:  MyProjectComponent},
            { path: 'view-council', component:  ViewCouncilComponent},
            { path: 'review-project', component: ReviewProjectComponent, canActivate: [TeacherGuard] },
            { path: 'approve-project', component: ListProjectComponent, canActivate: [TeacherGuard] },


            { path: 'uikit', loadChildren: () => import('./app/pages/uikit/uikit.routes') },
            { path: 'documentation', component: Documentation },

            { path: 'pages', loadChildren: () => import('./app/pages/pages.routes') }
        ]
    },
    { path: 'landing', component: Landing },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
