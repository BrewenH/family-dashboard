import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskListComponent } from '../../tasks/task-list/task-list.component'; // IMPORT CRUCIAL

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, TaskListComponent], // AJOUT ICI
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
}
