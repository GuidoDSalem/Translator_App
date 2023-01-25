//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Guido David Salem on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    var toText: String?
    var isTranslating: Bool
    var fromLanguage: UiLanguage
    var toLanguage: UiLanguage
    var onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslatingEvent: onTranslateEvent
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
        }
        else{
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

struct TranslateTextField_Previews: PreviewProvider {
    static var previews: some View {
        TranslateTextField(
            fromText: Binding(
                get: {"test"},
                set: {vale in}
            ),
            toText: "Test",
            isTranslating: false,
            fromLanguage: UiLanguage(language: .spanish, imageName: "spanish"),
            toLanguage: UiLanguage(language: .english, imageName: "english"),
            onTranslateEvent: {event in }
        )
    }
}

private extension TranslateTextField{
    struct IdTextField: View {
        @Binding var fromText: String
        var isTranslating: Bool
        var onTranslatingEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Color.onSurfaceColor)
                .overlay(alignment: .bottomTrailing){
                    ProgressButton(text: "Translate", isLoading: isTranslating, onClick: {
                        onTranslatingEvent(TranslateEvent.Translate())
                    })
                    .padding(.trailing)
                    .padding(.bottom)
                }
                
                .onAppear{
                    UITextView.appearance().backgroundColor = .clear
                }
        }
            
    }
    
    struct TranslatedTextField: View {
        var fromText: String
        var toText: String
        var fromLanguage: UiLanguage
        var toLanguage: UiLanguage
        var onTranslateEvent: (TranslateEvent) -> Void
        
        private let tts = TextToSpeech()
        
        var body: some View{
            VStack(alignment: .leading){
                LanguageDisplay(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(.onSurfaceColor)
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(fromText,forPasteboardType: UTType.plainText.identifier)
                        }
                    ){
                        Image(uiImage: UIImage(named:"copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: {
                        onTranslateEvent(TranslateEvent.CloseTranslation())
                        }
                    ){
                        Image(systemName: "xmark")
                            .foregroundColor(.lightBlue)
                    }
                    
                    
                }
                Divider()
                    .padding()
                
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                
                Text(toText)
                    .foregroundColor(Color.onSurfaceColor)
                
                HStack{
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(toText,forPasteboardType: UTType.plainText.identifier)
                        }
                    ){
                        Image(uiImage: UIImage(named:"copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: {
                        tts.speak(
                            text: toText,
                            language: toLanguage.language.langCode
                        )
                        }
                    ){
                        Image(systemName: "speaker.wave.2")
                            .foregroundColor(.lightBlue)
                    }
                    
                    
                }
                
            }
        }
    }
}
