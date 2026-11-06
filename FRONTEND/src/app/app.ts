import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HealthService } from './core/services/health.service';
import { LayoutComponent } from './features/dashboard/layout/layout.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, LayoutComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  healthStatus = signal('Chargement...');
  isBackendOk = signal<boolean | null>(null);

  constructor(private healthService: HealthService) {}

  ngOnInit(): void {
    this.healthService.getHealth().subscribe({
      next: () => {
        this.healthStatus.set('Backend : CONNECTÉ ✅');
        this.isBackendOk.set(true);
      },
      error: () => {
        this.healthStatus.set('Backend : DÉCONNECTÉ ❌');
        this.isBackendOk.set(false);
      }
    });
  }
}
