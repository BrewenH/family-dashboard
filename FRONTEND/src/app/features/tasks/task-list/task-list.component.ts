import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../../core/services/task.service'; // Correction du chemin
import { Task } from '../../../core/models/task.model'; // Correction du chemin

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.scss'
})
export class TaskListComponent implements OnInit {
  tasks = signal<Task[]>([]);

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => this.tasks.set(data), // On précise le type Task[]
      error: (err: any) => console.error('Erreur lors du chargement des tâches', err) // On précise any
    });
  }

  getPriorityColor(priority: string): string {
    switch (priority) {
      case 'HIGH': return '#ff4d4d';
      case 'MEDIUM': return '#ffcc00';
      case 'LOW': return '#4caf50';
      default: return '#888888';
    }
  }
}
