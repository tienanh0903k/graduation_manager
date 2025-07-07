
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-card',
  standalone: true,
  templateUrl: './product-card.html',
  styleUrls: ['./product-card.scss'],
})
export class ProductCardComponent {
  /* ───── Inputs ───── */
  @Input({ required: true }) productId!: string;
  @Input() disabled = false;

  /* ───── Outputs ───── */
  @Output() addToCart = new EventEmitter<string>();

  /* ───── Template‑facing props ───── */
  protected isLoading = false;

  /* ───── View‑queries ───── */
  // @ViewChild('img') image!: ElementRef<HTMLImageElement>;

  /* ───── Private fields ───── */
  private readonly destroy$ = new Subject<void>();

  /* ───── Constructor & DI ───── */
  constructor(private router: Router) {}

  /* ───── Lifecycle hooks ───── */
  ngOnInit(): void {
    this._loadProduct();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /* ───── Public methods / handlers ───── */
  onAddToCart(): void {
    this.addToCart.emit(this.productId);
  }

  /* ───── Private helpers ───── */
  private _loadProduct(): void {
    // …
  }
}
