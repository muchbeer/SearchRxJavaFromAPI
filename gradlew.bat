����   2 u ,kotlin/sequences/USequencesKt___USequencesKt  java/lang/Object  	sumOfUInt (Lkotlin/sequences/Sequence;)I -(Lkotlin/sequences/Sequence<Lkotlin/UInt;>;)I Lkotlin/jvm/JvmName; name Lkotlin/SinceKotlin; version 1.3 "Lkotlin/ExperimentalUnsignedTypes; #Lorg/jetbrains/annotations/NotNull; 	$this$sum  kotlin/jvm/internal/Intrinsics  checkParameterIsNotNull '(Ljava/lang/Object;Ljava/lang/String;)V  
   kotlin/sequences/Sequence  iterator ()Ljava/util/Iterator;     java/util/Iterator  hasNext ()Z     ! next ()Ljava/lang/Object; # $  % kotlin/UInt ' 
unbox-impl ()I ) *
 ( + constructor-impl (I)I - .
 ( / element I sum Lkotlin/sequences/Sequence; 
sumOfULong (Lkotlin/sequences/Sequence;)J .(Lkotlin/sequences/Sequence<Lkotlin/ULong;>;)J kotlin/ULong 8 ()J ) :
 9 ; (J)J - =
 9 > J 
sumOfUByte .(Lkotlin/sequences/Sequence<Lkotlin/UByte;>;)I kotlin/UByte C ()B ) E
 D F B sumOfUShort /(Lkotlin/sequences/Sequence<Lkotlin/UShort;>;)I kotlin/UShort K ()S ) M
 L N  �� S Lkotlin/Metadata; mv       bv        k    xi d1 ���"
��







��0*00Hø��¢��0*00Hø��¢��0*00Hø��¢	��0*0
0Hø��¢
¨ d2 Lkotlin/UInt; Lkotlin/UByte; Lkotlin/ULong; Lkotlin/UShort; kotlin-stdlib xs kotlin/sequences/USequencesKt _USequences.kt <init> ()V g h
  i Code StackMapTable LineNumberTable LocalVariableTable 	Signature RuntimeInvisibleAnnotations $RuntimeInvisibleParameterAnnotations 
SourceFile SourceDebugExtension RuntimeVisibleAnnotations              k   �     8*� <*�  N-� " � !-� & � (� ,=66`� 0<����    l    �   & m         %  3  6  n      %  1 2   0 3 2    8  4   o     p       	s  
  s     q         5 6  k   �  	   ;*� 	@*�  :� " � "� & � 9� <B76!a� ?@����    l    �        ( m      +  , ( - 6 , 9 / n      (  1 @   3 3 @    ;  4   o    7 p       	s 5 
  s     q         A   k   �  	   Q*� <*�  N-� " � :-� & � D� G=66