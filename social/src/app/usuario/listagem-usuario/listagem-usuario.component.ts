import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-listagem-usuario',
  templateUrl: './listagem-usuario.component.html',
  styleUrls: ['./listagem-usuario.component.scss']
})
export class ListagemUsuarioComponent implements OnInit {

  usuarios = [
    {nome: 'Usuário 1', cpf: '123', idade: 30},
    {nome: 'Usuário 2', cpf: '234', idade: 31},
    {nome: 'Usuário 3', cpf: '345', idade: 32}
  ];

  titulo = 'Listagem Usuários';

  constructor() { }

  ngOnInit(): void {
  }

  alterarTitulo(novoTitulo: string): void {
    this.titulo = novoTitulo;
  }

}
