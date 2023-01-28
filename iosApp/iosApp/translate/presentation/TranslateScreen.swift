//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Guido David Salem on 20/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared


struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: Translate
    @ObservedObject var viewModel: IOSTranslateViewModel
    private let parser: any VoiceToTextParser
    
    init(historyDataSource: HistoryDataSource, translateUseCase: Translate,parser: VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.parser = parser
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource ,
            translateUseCase: translateUseCase
        )
    }

    var body: some View {
        ZStack{
            List{
                HStack(alignment: .center){
                    LanguageDropDown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectLanguage: { language_ in
                            viewModel.onEvent(event: .ChooseFromLanguage(language: language_))
                        })
                    
                    Spacer()
                    
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: .SwapLanguages())
                    })
                    
                    Spacer()
                    
                    LanguageDropDown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectLanguage: { language_ in
                            viewModel.onEvent(event: .ChooseToLanguage(language: language_))
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.backgroundColor)
                
                TranslateTextField(
                    fromText: Binding(
                        get: {viewModel.state.fromText},
                        set: {value in
                            viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                            
                        }),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: {viewModel.onEvent(event: $0)}
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.backgroundColor)
                
                if !viewModel.state.history.isEmpty{
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity,alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.backgroundColor)
                }
                
                ForEach(viewModel.state.history,id: \.self.id){ item in
                    TranslateHistoryItem(
                        item: item,
                        onClick: {
                            viewModel.onEvent(event: .SelectHistoryItem(item: item))
                        }
                    )
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.backgroundColor)
                }
                
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack{
                Spacer()
                NavigationLink(destination: VoiceToTextScreen(
                    onResult: { spokenText in
                        viewModel.onEvent(event: .SubmitVoiceResult(result: spokenText))
                        
                    },
                    parser: parser,
                    languageCode: viewModel.state.fromLanguage.language.langCode)){
                    ZStack{
                        Circle()
                            .foregroundColor(.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundColor(.onPrimaryColor)
                            .accessibilityIdentifier("Record audio")
                    }
                    .frame(maxWidth: 100,maxHeight: 100)
                }
            }
        }
        .onAppear{
            viewModel.startObserving()
        }
        .onDisappear{
            viewModel.dispose()
        }
    }
}

//struct TranslateScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        TranslateScreen()
//    }
//}
