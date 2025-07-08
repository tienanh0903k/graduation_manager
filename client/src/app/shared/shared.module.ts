import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { InputNumberModule } from 'primeng/inputnumber';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';

import { TruncatePipe } from './pipes/truncate.pipe';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ToastModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    ConfirmDialogModule,
    TagModule,
    InputNumberModule,
    TableModule,
    DropdownModule,
    PanelModule,
    TruncatePipe
  ],
  exports: [
    CommonModule,
    FormsModule,
    ToastModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    ConfirmDialogModule,
    TagModule,
    InputNumberModule,
    TableModule,
    DropdownModule,
    PanelModule,
    TruncatePipe
  ]
})
export class SharedModule {}
