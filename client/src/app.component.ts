import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProgressBarModule } from 'primeng/progressbar';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule, ProgressBarModule, CommonModule],
    template: `
     <p-progressBar
            *ngIf="loading"
            mode="indeterminate"
            styleClass="top-loading-bar">
        </p-progressBar>
    <router-outlet></router-outlet>
    `,
    styleUrl: './styles.scss'
})
export class AppComponent {
    loading = true;

    constructor() {
        setTimeout(() => {
            this.loading = false;
        }, 3000);
    }
}

