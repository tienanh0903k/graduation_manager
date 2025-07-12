import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SharedModule } from '../../../../shared/shared.module';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import * as XLSX from 'xlsx';

interface Student {
    mssv: string;
    name: string;
    email: string;
    class_name: string;
    gender: string;
}

@Component({
    standalone: true,
    imports: [SharedModule, InputTextModule, ButtonModule, DialogModule, TableModule],
    selector: 'app-import-excel-dialog',
    templateUrl: './import-excel-dialog.component.html'
})
export class ImportExcelDialogComponent {
    @Input() visible = false;
    @Output() visibleChange = new EventEmitter<boolean>();
    @Output() importStudents = new EventEmitter<any[]>();

    header = 'Nhập danh sách sinh viên từ Excel';
    importPreview: any[] = [];
    selectedImportRows: any[] = [];

    onExcelFileSelected(event: any) {
        const file = event.target.files?.[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = (e: any) => {
            try {
                const data = new Uint8Array(e.target.result);
                const workbook = XLSX.read(data, { type: 'array' });
                const sheetName = workbook.SheetNames[0];
                const sheet = workbook.Sheets[sheetName];
                const jsonData = XLSX.utils.sheet_to_json(sheet);

                this.importPreview = jsonData.map((item: any) => ({
                    mssv: item['MSSV'] || item['Mã sinh viên'] || '',
                    name: item['Họ tên'] || item['Tên'] || '',
                    email: item['email'] || '',
                    className: item['Lớp'] || item['Lớp học'] || '',
                    password: item['Mật khẩu'] || ''
                }));

                console.log('Parsed data:', this.importPreview);
            } catch (error) {
                console.error('Error parsing Excel file:', error);
                alert('Có lỗi khi đọc file Excel. Vui lòng kiểm tra định dạng file.');
            }
        };
        reader.readAsArrayBuffer(file);
    }

    onImport() {
        if (!this.selectedImportRows.length) {
            alert('Vui lòng chọn ít nhất 1 sinh viên để nhập!');
            return;
        }

        console.log('Importing students:', this.selectedImportRows);
        this.importStudents.emit([...this.selectedImportRows]);
        this.hideDialog();
    }

    onCancel() {
        this.hideDialog();
    }

    private hideDialog() {
        this.visible = false;
        this.visibleChange.emit(this.visible);

        this.importPreview = [];
        this.selectedImportRows = [];
    }
}
