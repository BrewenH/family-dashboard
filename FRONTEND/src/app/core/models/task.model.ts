export interface Task {
  id: number;
  title: string;
  description: string;
  status: string;
  priority: string;
  dueDate: string;
  assignedMember?: {
    displayName: string;
    color: string;
  };
}
