import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatDividerModule,
    MatButtonToggleModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  hidePassword = true;
  selectedRole: 'patient' | 'staff' = 'patient';

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      identifier: ['', [Validators.required, Validators.minLength(3)]], // Email or Health ID
      password: ['', [Validators.required, Validators.minLength(6)]],
      remember: [false]
    });
  }
  
  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
    const payload = {
      role: this.selectedRole,
      ...this.loginForm.value
    };
    // TODO: call authService.login(payload). For demo:
    console.log('login submit', payload);
  }

  toggleRole(role: 'patient' | 'staff') {
    this.selectedRole = role;
  }
}
