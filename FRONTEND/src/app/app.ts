import { Component, OnInit, signal } from '@angular/core'; // <--- On ajoute 'signal'
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HealthService } from './core/services/health.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  // On transforme les variables en SIGNALS
  healthStatus = signal('Chargement...');
  isBackendOk = signal<boolean | null>(null);

  constructor(private healthService: HealthService) {}

  ngOnInit(): void {
    this.healthService.getHealth().subscribe({
      next: () => {
        // Pour modifier un signal, on utilise .set()
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
