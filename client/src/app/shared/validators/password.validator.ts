
import { AbstractControl, ValidationErrors } from '@angular/forms';

export class PasswordValidator {
  static strong(control: AbstractControl): ValidationErrors | null {
    const value: string = control.value || '';

    const strongRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;

    return strongRegex.test(value)
      ? null
      : {
          passwordStrength: {
            message:
              'Mật khẩu phải có ít nhất 6 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.',
          },
        };
  }
}
