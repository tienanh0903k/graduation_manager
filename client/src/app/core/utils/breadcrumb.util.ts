export class BreadcrumbUtil {
  static getLabelFromPath(path: string): string {
    const mapping: { [key: string]: string } = {
      '/create-project': 'Tạo đề tài',
      '/dashboard': 'Bảng điều khiển',
      '/project-list': 'Danh sách đề tài',
      '/home': 'Trang chủ'
    };

    return mapping[path] || 'Không rõ';
  }
}
