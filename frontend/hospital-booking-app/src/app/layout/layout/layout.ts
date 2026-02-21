import { Component } from '@angular/core';

import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../header/header/header';
import { SidebarComponent } from '../sidebar/sidebar/sidebar';


@Component({
  selector: 'app-layout',
  imports: [RouterOutlet,
    HeaderComponent,
    SidebarComponent],
  templateUrl: './layout.html',
  styleUrl: './layout.sass',
})
export class Layout {

}
